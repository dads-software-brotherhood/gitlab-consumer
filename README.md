# Gitlab api consumer

Read info for stats from gitlap api v4

> Only support v4 version

## Configuration

Edit your `application.yml` and add:

```yaml
application:
    gitlab:
        api-url: <gitlab-api-v4-url>
        security:
            token: <token>
            token-header-name: private_token
```

> Remplace with actual values.
