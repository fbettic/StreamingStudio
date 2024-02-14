## Pasos para crear base de datos das

Crear container:

```bash
docker run --name ServerDAS -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=Chino@1234" -e "MSSQL_COLLATION=SQL_Latin1_General_CP1_CS_AS" -p 1433:1433 --hostname das -d mcr.microsoft.com/mssql/server:2022-latest
```

Conectarse al co mediante consola:

```bash
docker exec -it ServerDAS "bash"
```

Conectarse al servidor:

```bash
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "Chino@1234"
```

