package com.example.proxybandtesttask.model;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("users")
public class User {
    @MongoId(value = FieldType.INT64)
    @Builder.Default
    private Long id = (long) Math.abs(Objects.hashCode(ObjectId.get()));
    private String name;
    @Indexed(unique = true)
    private String email;
}
