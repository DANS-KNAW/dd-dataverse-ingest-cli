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

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.knaw.dans.dvingest.client.DefaultApi;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
@Command(name = "get-import-status")
public class GetImportStatus implements Callable<Integer> {
    @NonNull
    private final DefaultApi api;

    @NonNull
    private final ObjectMapper objectMapper;

    @Parameters(index = "0", paramLabel = "location", description = "The path to the import batch", arity = "0..1")
    private String location;

    @Override
    public Integer call() {
        try {
            var status = api.ingestGet(location);
            System.out.println(objectMapper.writeValueAsString(status));
            System.err.println("Import status retrieved: " + location);
        }
        catch (Exception e) {
            System.err.println("Error getting import status: " + e.getMessage());
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

}
