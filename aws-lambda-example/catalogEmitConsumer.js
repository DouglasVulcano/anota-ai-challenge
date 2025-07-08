import { S3Client, GetObjectCommand, PutObjectCommand } from "@aws-sdk/client-s3";

const s3 = new S3Client({ region: 'sa-east-1' });

export const handler = async (event) => {
  try {
    console.log("Iniciando processamento do evento...");

    for (const record of event.Records) {
      let body;

      try {
        const rowBody = JSON.parse(record.body);
        body = JSON.parse(rowBody.Message);
        console.log("Mensagem recebida e parseada com sucesso:", body);
      } catch (err) {
        console.log("Erro ao fazer parse da mensagem:", err);
        continue;
      }

      const ownerId = body.ownerId;
      const bucketName = "anota-ai-challenge-catalog-marketplace";
      const fileName = `${ownerId}-catalog.json`;

      try {
        console.log(`Tentando recuperar catálogo do S3: ${fileName}`);
        const catalog = await getS3Object(bucketName, fileName);
        const catalogJson = JSON.parse(catalog);

        console.log(`Catálogo existente recuperado com sucesso para o ownerId ${ownerId}`);

        if (body.type === "product") {
          console.log("Atualizando/inserindo produto...");
          updateOrAddItem(catalogJson.products, body);
        } else {
          console.log("Atualizando/inserindo categoria...");
          updateOrAddItem(catalogJson.categories, body);
        }

        await putS3Object(bucketName, fileName, JSON.stringify(catalogJson));
        console.log(`Catálogo atualizado com sucesso: ${fileName}`);
      } catch (error) {
        if (error.message === "Error getting object from bucket") {
          console.log(`Catálogo não encontrado para ${fileName}, criando novo...`);
          const newCatalog = { products: [], categories: [] };

          if (body.type === "product") {
            newCatalog.products.push(body);
          } else {
            newCatalog.categories.push(body);
          }

          await putS3Object(bucketName, fileName, JSON.stringify(newCatalog));
          console.log(`Novo catálogo criado com sucesso para ${ownerId}`);
        } else {
          console.log('Erro inesperado ao processar item:', error);
        }
      }
    }

    console.log("Processamento do evento finalizado com sucesso.");
    return {
      statusCode: 200,
      body: JSON.stringify('Mensagem processada com sucesso')
    };
  } catch (error) {
    console.log('Erro ao processar evento:', error);
    throw new Error("Erro ao processar a mensagem SQS");
  }
};

const updateOrAddItem = (items, newItem) => {
  const index = items.findIndex(item => item.id == newItem.id);

  if (index !== -1) {
    console.log(`Item com ID ${newItem.id} encontrado, atualizando...`);
    items[index] = { ...items[index], ...newItem };
  } else {
    console.log(`Item com ID ${newItem.id} não encontrado, adicionando...`);
    items.push(newItem);
  }
};

const streamToString = (stream) =>
  new Promise((resolve, reject) => {
    const chunks = [];
    stream.on("data", (chunk) => chunks.push(chunk));
    stream.on("error", reject);
    stream.on("end", () => resolve(Buffer.concat(chunks).toString("utf8")));
  });

const getS3Object = async (bucketName, fileName) => {
  const command = new GetObjectCommand({
    Bucket: bucketName,
    Key: fileName
  });

  try {
    const response = await s3.send(command);
    return streamToString(response.Body);
  } catch (error) {
    console.log("Erro ao buscar objeto no bucket:", error.message);
    throw new Error("Error getting object from bucket");
  }
};

async function putS3Object(bucketName, fileName, body) {
  try {
    console.log(`Salvando objeto no bucket: ${fileName}`);
    const command = new PutObjectCommand({
      Bucket: bucketName,
      Key: fileName,
      Body: body,
      ContentType: 'application/json'
    });

    const putResult = await s3.send(command);
    console.log("Objeto salvo com sucesso:", putResult);
    return putResult;
  } catch (error) {
    console.log('Erro ao salvar objeto no bucket:', error);
    return;
  }
}
