package com.vitoria.Turing.Machine.controller;

import com.vitoria.Turing.Machine.dto.ExecutionResultDTO;
import com.vitoria.Turing.Machine.model.ExecutionStrategy;
import com.vitoria.Turing.Machine.model.NumberFormat;
import com.vitoria.Turing.Machine.model.RequestedStrategy;
import com.vitoria.Turing.Machine.parser.BinaryParser;
import com.vitoria.Turing.Machine.service.PrimalityCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@RestController("/check")
public class PrimalityCheckController {

    @Autowired
    private PrimalityCheckService service;

    @PostMapping("/decimal/{number}/{requestedStrategy}")
    public ResponseEntity<List<ExecutionResultDTO>> check(@PathVariable BigInteger number, @PathVariable RequestedStrategy requestedStrategy) {
        System.out.println("number: " + number);
        System.out.println("strategy: " + requestedStrategy.toString());
        LinkedList<Character> binaryNumber = BinaryParser.parse(number);
        return new ResponseEntity<>(check(binaryNumber, number, NumberFormat.DECIMAL, requestedStrategy), HttpStatus.OK);
    }

    @PostMapping("/binary/{number}/{requestedStrategy}")
    public ResponseEntity<List<ExecutionResultDTO>> check(@PathVariable String number, @PathVariable RequestedStrategy requestedStrategy) {
        LinkedList<Character> binaryNumber = BinaryParser.formAsLinkedList(number);
        return new ResponseEntity<>(check(binaryNumber, BinaryParser.parse(binaryNumber), NumberFormat.BINARY, requestedStrategy), HttpStatus.OK);
    }


    private List<ExecutionResultDTO> check(LinkedList<Character> binaryNumber, BigInteger decimalNumber, NumberFormat originalNumberFormat, RequestedStrategy requestedStrategy) {
        List<ExecutionResultDTO> results = new LinkedList<>();

        if(requestedStrategy != RequestedStrategy.OPTIMIZED)
            results.add(service.check(binaryNumber, decimalNumber, originalNumberFormat, ExecutionStrategy.BASE));

        if(requestedStrategy != RequestedStrategy.BASE)
            results.add(service.check(binaryNumber, decimalNumber, originalNumberFormat, ExecutionStrategy.OPTIMIZED));

        return results;
    }
}
