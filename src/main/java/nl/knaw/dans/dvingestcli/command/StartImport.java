/*
 * Copyright (C) 2024 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.dvingestcli.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.knaw.dans.dvingest.api.ImportCommandDto;
import nl.knaw.dans.dvingest.client.ApiException;
import nl.knaw.dans.dvingest.client.DefaultApi;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "start-import",
         mixinStandardHelpOptions = true,
         description = "Start an import job")
@RequiredArgsConstructor
public class StartImport implements Callable<Integer> {
    @NonNull
    private final DefaultApi api;

    @NonNull
    private final ObjectMapper objectMapper;

    @Parameters(index = "0", paramLabel = "path", description = "The path to the deposit or batch to import")
    private Path path;

    @Option(names = { "-s", "--single-deposit" }, description = "Import as single deposit")
    private boolean singleDeposit;

    @Option(names = { "-c", "--continue-batch"}, description = "Continue a previously started batch (will skip check for empty output directory)")
    private boolean continueBatch;

    @Override
    public Integer call() {
        try {
            var canonicalPath = path.toRealPath().toString();
            var status = api.ingestPost(
                new ImportCommandDto()
                    .path(canonicalPath)
                    .migration(false)
                    .singleObject(singleDeposit)
                    .continueBatch(continueBatch));
            System.out.println(objectMapper.writeValueAsString(status));
            System.err.println("Import started: " + canonicalPath);
        }
        catch (JsonProcessingException e) {
            System.err.println("Status report could not be parsed: " + e.getMessage());
            return 1;
        }
        catch (ApiException | IOException e) {
            System.err.println("Error starting import: " + e.getMessage());
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
