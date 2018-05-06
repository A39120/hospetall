#!/bin/zsh
user=worker
server=localhost
password=1234567890
ins=_insert.sql
PATH="$HOME/LI61D-LI61N-G35/database"

/bin/sqlcmd -S $server -U $user -P $password -i $PATH/drop.sql
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/tables.sql
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/person$ins
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/client$ins
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/nurse$ins
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/vet$ins

