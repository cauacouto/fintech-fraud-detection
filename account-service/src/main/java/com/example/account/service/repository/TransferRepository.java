package com.example.account.service.repository;

import com.example.account.service.domin.Transfer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface TransferRepository extends MongoRepository<Transfer, UUID> {
}
