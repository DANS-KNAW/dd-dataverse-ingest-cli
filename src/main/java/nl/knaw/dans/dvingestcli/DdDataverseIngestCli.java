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

package nl.knaw.dans.dvingestcli;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.knaw.dans.dvingest.client.ApiClient;
import nl.knaw.dans.dvingest.client.DefaultApi;
import nl.knaw.dans.dvingestcli.command.ConvertDansBag;
import nl.knaw.dans.dvingestcli.command.GetImportStatus;
import nl.knaw.dans.dvingestcli.command.StartImport;
import nl.knaw.dans.dvingestcli.config.DdDataverseIngestCliConfig;
import nl.knaw.dans.lib.util.AbstractCommandLineApp;
import nl.knaw.dans.lib.util.ClientProxyBuilder;
import nl.knaw.dans.lib.util.PicocliVersionProvider;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "ingest",
         mixinStandardHelpOptions = true,
         versionProvider = PicocliVersionProvider.class,
         description = "CLI for dd-dataverse-ingest")
@Slf4j
public class DdDataverseIngestCli extends AbstractCommandLineApp<DdDataverseIngestCliConfig> {
    public static void main(String[] args) throws Exception {
        new DdDataverseIngestCli().run(args);
    }

    public String getName() {
        return "CLI for dd-dataverse-ingest";
    }

    @Override
    public void configureCommandLine(CommandLine commandLine, DdDataverseIngestCliConfig config) {
        var objectMapper = new ObjectMapper();
        DefaultApi api = new ClientProxyBuilder<ApiClient, DefaultApi>()
            .apiClient(new ApiClient())
            .defaultApiCtor(DefaultApi::new)
            .httpClient(config.getDataverseIngest().getHttpClient())
            .basePath(config.getDataverseIngest().getUrl()).build();

        log.debug("Configuring command line");
        commandLine.addSubcommand(new StartImport(api, objectMapper));
        commandLine.addSubcommand(new GetImportStatus(api, objectMapper));
        commandLine.addSubcommand(new ConvertDansBag(api, objectMapper));
    }
}
