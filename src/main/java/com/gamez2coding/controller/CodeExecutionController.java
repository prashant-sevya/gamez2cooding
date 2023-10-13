package com.gamez2coding.controller;
import com.gamez2coding.dto.CodeRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/gamez2coding/api/")
public class CodeExecutionController {
    @PostMapping("/execute-code")
    public String executePythonCode(@RequestBody CodeRequest codeRequest) {
        System.out.println("prashant"+ codeRequest.getLanguage());
        String language = codeRequest.getLanguage();
        System.out.println("language"+language);
        if (language.equals("python")) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("python", "-c", codeRequest.getCode());
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    return "Code executed successfully. Output:\n" + output.toString();
                } else {
                    return "Code execution failed. Output:\n" + output.toString();
                }
            } catch (IOException | InterruptedException e) {
                return "Code execution failed: " + e.getMessage();
            }
        }
        if(language.equals("java")){
            try {
                String sourceFileName = "HelloWorld.java";
                String className = "HelloWorld";
                Files.write(Paths.get(sourceFileName), codeRequest.getCode().getBytes());
                Process compileProcess = new ProcessBuilder("javac", sourceFileName).start();
                String compileErrorOutput = getProcessOutput(compileProcess.getErrorStream());
                int compileExitCode = compileProcess.waitFor();
                if (compileExitCode == 0) {
                    Process runProcess = new ProcessBuilder("java", className).start();
                    String runOutput = getProcessOutput(runProcess.getInputStream());
                    int runExitCode = runProcess.waitFor();
                    if (runExitCode == 0) {
                        return "Java code executed successfully. Output:\n" + runOutput;
                    } else {
                        return "Java code execution failed. Output:\n" + runOutput;
                    }
                } else {
                    return "Java code compilation failed. Error Output:\n" + compileErrorOutput;
                }
            } catch (IOException | InterruptedException e) {
                return "Java code execution failed: " + e.getMessage();
            } finally {
                File sourceFile = new File("HelloWorld.java");
                sourceFile.delete();
            }
        }
        if(language.equals("javascript")){
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("node", "-e", codeRequest.getCode());
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    return "JavaScript code executed successfully. Output:\n" + output.toString();
                } else {
                    return "JavaScript code execution failed. Output:\n" + output.toString();
                }
            } catch (IOException | InterruptedException e) {
                return "JavaScript code execution failed: " + e.getMessage();
            }
        }
        return codeRequest.getLanguage()+" language has not support";
    }
    private String getProcessOutput(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return output.toString();
    }
}
