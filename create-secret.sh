#!/bin/bash

echo "Aguardando o LocalStack..."
sleep 3

export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1

JSON=$(jq -n \
          --arg bd_user "$MYSQL_USER" \
          --arg bd_pass "$MYSQL_PASSWORD" \
          --arg k_user "$KEYCLOAK_USER" \
          --arg k_pass "$KEYCLOAK_PASSWORD" \
          --arg k_admin_client "$KEYCLOAK_ADMIN_CLIENT_ID" \
          --arg k_admin_realm "$KEYCLOAK_ADMIN_REALM" \
          --arg k_app_realm "$KEYCLOAK_APP_REALM" \
          --arg email_user "$EMAIL_USER" \
          --arg email_key "$EMAIL_KEY" \
          '{
              "bd.user": $bd_user,
              "bd.password": $bd_pass,
              "keycloak.admin.user": $k_user,
              "keycloak.admin.password": $k_pass,
              "keycloak.admin.client": $k_admin_client,
              "keycloak.admin.realm": $k_admin_realm,
              "keycloak.app.realm": $k_app_realm,
              "email.user": $email_user,
              "email.key": $email_key
          }')
aws --endpoint-url=http://localstack:4566 secretsmanager create-secret --name /secret/car-rental_default --secret-string "$JSON"
