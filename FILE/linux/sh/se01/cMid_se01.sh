#!bin/bash

docker restart se01

docker exec se01 sh -c "cd compile; rm -r cTest;"
docker exec se01 sh -c "cd compile; rm -r cError.txt;"
docker exec se01 sh -c "cd compile; rm -r cResult.txt;"
docker exec se01 sh -c "cd compile; rm -r cTest.c";

docker exec se01 sh -c "cd data; mv cTest.txt ../compile"
docker exec se01 sh -c "cd compile; cp cTest.txt cTest.c"
docker exec se01 sh -c "cd compile; gcc -o cTest cTest.c 2> cError.txt"
docker exec se01 sh -c "cd compile; ./cTest > cResult.txt"
docker exec se01 sh -c "cd compile; mv cResult.txt ../data"
docker exec se01 sh -c "cd compile; mv cError.txt ../data "

