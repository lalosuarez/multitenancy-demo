#!/bin/bash

port=${1:-8020}
dir=${2:-$PWD}

cd $d
jwebserver -d $dir -p $port
