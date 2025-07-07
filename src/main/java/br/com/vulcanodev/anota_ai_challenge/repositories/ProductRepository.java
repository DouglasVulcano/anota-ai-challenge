package br.com.vulcanodev.anota_ai_challenge.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.vulcanodev.anota_ai_challenge.domain.product.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
