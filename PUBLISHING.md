# Publishing SEE Java SDK

## Before Releasing

1. Set the release version in `pom.xml` and update the README dependency example.
2. Run the Java 17 build:

   ```bash
   mvn clean verify
   ```

3. Confirm that no credentials or local Maven settings are staged. The repository
   ignores its root `settings.xml`; prefer `~/.m2/settings.xml` for local credentials.

## GitHub Release and Packages

Push a semantic version tag matching `v*.*.*`:

```bash
git tag v1.3.0
git push origin v1.3.0
```

The GitHub Actions workflows build with JDK 17, create a GitHub Release artifact,
and deploy to GitHub Packages using the repository-provided `GITHUB_TOKEN`.

## Maven Central

The build uses the Central Publishing Maven Plugin with server ID `central`.
Configure a Central Portal user token outside the repository:

```xml
<settings>
  <servers>
    <server>
      <id>central</id>
      <username>${env.CENTRAL_USERNAME}</username>
      <password>${env.CENTRAL_TOKEN}</password>
    </server>
  </servers>
</settings>
```

Publish only from a trusted release environment. Never commit a generated
`settings.xml`, token, private key, or passphrase. Verify completed deployments
in the [Central Publisher Portal](https://central.sonatype.com/publishing).
