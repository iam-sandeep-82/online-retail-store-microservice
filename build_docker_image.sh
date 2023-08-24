#!/bin/bash
src_dir=$2
tag=$1

cd $PWD/$src_dir/ && docker build -t $tag -f Dockerfile .
