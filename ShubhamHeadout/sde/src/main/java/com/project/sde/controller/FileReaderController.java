package com.project.sde.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


@RestController
public class FileReaderController {
    private static final String DATA_DIR = "tmp\\data";

    @GetMapping("/data")
    public String getData(@RequestParam(name = "n") String fileName,
                          @RequestParam(name = "m", required = false) Integer lineNumber) {
        String filePath = DATA_DIR + "\\" + fileName + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            if (lineNumber != null) {
                String[] lines = content.toString().split("\n");
                if (lineNumber > 0 && lineNumber <= lines.length) {
                    return lines[lineNumber - 1];
                } else {
                    return "Line number out of range";
                }
            } else {
                return content.toString().trim(); // Trim to remove the trailing newline
            }
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }
}
