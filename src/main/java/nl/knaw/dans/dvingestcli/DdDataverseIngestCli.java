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

import lombok.extern.slf4j.Slf4j;
import nl.knaw.dans.lib.util.AbstractCommandLineApp;
import nl.knaw.dans.lib.util.PicocliVersionProvider;
import nl.knaw.dans.dvingestcli.config.DdDataverseIngestCliConfig;
import picocli.AutoComplete.GenerateCompletion;
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
        // TODO: set up the API client, if applicable
        log.debug("Configuring command line");
        // TODO: add options and subcommands
    }
}
