#!/bin/zsh
user=worker
server=localhost
password=1234567890
PATH="$HOME/LI61D-LI61N-G35/database"

/bin/sqlcmd -S $server -U $user -P $password -i $PATH/client.sql
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/veterinarian.sql
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/nurse.sql
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/species.sql
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/race.sql
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/pet.sql
/bin/sqlcmd -S $server -U $user -P $password -i $PATH/consultation.sql
