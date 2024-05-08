package com.miko.Assignment.controller;

import com.miko.Assignment.model.Input;
import com.miko.Assignment.services.AssemblyLanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/assembly")
public class AssemblyController {
    @Autowired
    private AssemblyLanguageService assemblyProgram;

    @Operation(
            summary = "Executes the Assembly Language Program having 2 Registers",
            method = "POST"
    )
    @PostMapping("/execute")
    public ResponseEntity<String> executeProgram(/*
            @Parameter(description = "The assembly program to execute") @RequestParam("program") String[] program*/
    @RequestBody Input input) {
        log.info("inside controller {}", Arrays.stream(input.getInstructions()).toList());
        return ResponseEntity.ok(assemblyProgram.execute(input.getInstructions()));
    }
}
