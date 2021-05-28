from pathlib import Path
import os
import argparse
import subprocess
from sys import platform
import re
import shutil

script_dir = Path(__file__).resolve().parent
os.chdir(script_dir)

URLS = {
    "alpha": "https://knyio2nl7d.execute-api.us-east-1.amazonaws.com/dev/graphql",
    "prod": "https://5orf8nzr57.execute-api.us-east-1.amazonaws.com/production/graphql",
    "dev": "https://18wi8h43il.execute-api.us-east-1.amazonaws.com/dev-flipted/graphql"
}

parser = argparse.ArgumentParser(description='Retrieve the most up-to-date Apollo GraphQL schema')
parser.add_argument('--env', choices=['alpha', 'dev', 'prod'], default='dev')
parser.add_argument('token')
args = parser.parse_args()

url = URLS[args.env]

if platform == 'win32':
    gradlew = 'gradlew.bat'
else:
    gradlew = './gradlew'

print("Downloading {} schema ({})".format(args.env, url))
subprocess.run('{} downloadApolloSchema --schema=app/src/main/graphql/edu/calpoly/flipted/schema.json --endpoint={} --header="Authorization: {}"'.format(gradlew, url, args.token), shell=True)

apolloRepo = Path('./app/src/main/java/edu/calpoly/flipted/backend/ApolloRepo.kt')
tmpApolloRepo = apolloRepo.parent / (apolloRepo.name + '.tmp')

expr = re.compile('^(\s+const val BACKEND_URL = )"[a-zA-Z0-9\.:\/-]+"\s*$')

with apolloRepo.open('r') as arf, tmpApolloRepo.open('w') as tf:
    for l in arf.readlines():
        match = expr.fullmatch(l)
        if match:
            tf.write("{}\"{}\"\n".format(match.group(1), url))
        else:
            tf.write(l)

apolloRepo.unlink()
shutil.move(tmpApolloRepo, apolloRepo)
