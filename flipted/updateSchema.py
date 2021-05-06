import subprocess
URL = "https://knyio2nl7d.execute-api.us-east-1.amazonaws.com/dev/graphql"

subprocess.run(["gradlew.bat", "downloadApolloSchema", "--schema=app/src/main/graphql/edu/calpoly/flipted/schema.json", "--endpoint={}".format(URL)], shell=True)
