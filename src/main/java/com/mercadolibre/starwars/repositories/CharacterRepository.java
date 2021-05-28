package com.mercadolibre.starwars.repositories;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.starwars.dto.CharacterDTO;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CharacterRepository {
    private List<CharacterDTO> data;

    public List<CharacterDTO> findAllWithName(String name) {
        return data.stream()
                .filter(character -> character.getName().toUpperCase().contains(name.toUpperCase()))
                .collect(Collectors.toList());
    }

    @PostConstruct
    private void loadDataBase() throws IOException {
        File characterDataBase = getDabaBase();
        if(characterDataBase.length() != 0) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<CharacterDTO>> characterReference = new TypeReference<>() { };
            this.data = mapper.readValue(characterDataBase, characterReference);
        }
    }

    private File getDabaBase() throws FileNotFoundException {
        return ResourceUtils.getFile("classpath:static/starwars.json");
    }


}
