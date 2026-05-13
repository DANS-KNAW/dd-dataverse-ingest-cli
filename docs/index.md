dd-dataverse-ingest-cli
=======================

CLI for dd-dataverse-ingest

SYNOPSIS
--------

```bash
ingest start-import [ -s | --single-deposit ] [ -c | --continue-batch ] <path>
ingest get-import-status [ <location> ]
ingest cancel-import <path>
ingest convert-dans-import-bag [ -s | --single-deposit ] <path>
```

DESCRIPTION
-----------

The `dd-dataverse-ingest-cli` is a command-line interface for the `dd-dataverse-ingest` service. It allows you to manage import jobs.

Use `ingest <command> --help` for more information on a specific command.