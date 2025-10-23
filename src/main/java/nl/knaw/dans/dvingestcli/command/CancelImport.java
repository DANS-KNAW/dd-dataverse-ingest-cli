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
import nl.knaw.dans.dvingest.client.ApiException;
import nl.knaw.dans.dvingest.client.DefaultApi;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "cancel-import",
         mixinStandardHelpOptions = true,
         description = "Cancel an import job")
@RequiredArgsConstructor
public class CancelImport implements Callable<Integer> {
    @NonNull
    private final DefaultApi api;

    @NonNull
    private final ObjectMapper objectMapper;

    @Parameters(index = "0", paramLabel = "path", description = "The path to the deposit or batch to cancel")
    private String path;

    @Override
    public Integer call() {
        try {
            var statuses = api.ingestCancelPost(path);
            System.out.println(objectMapper.writeValueAsString(statuses));
            System.err.println("Cancelation request sent. Running task will be completed, but no new tasks will be started.");
        }
        catch (JsonProcessingException e) {
            System.err.println("Status report could not be parsed: " + e.getMessage());
            return 1;
        }
        catch (ApiException e) {
            System.err.println("Error canceling import: " + e.getMessage());
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
