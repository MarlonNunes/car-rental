#!/bin/bash

echo "Aguardando o LocalStack..."
sleep 3

export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1

JSON=$(jq -n \
          --arg bd_user "root" \
          --arg bd_pass "$MYSQL_ROOT_PASSWORD" \
          --arg k_user "$KEYCLOAK_ADMIN" \
          --arg k_pass "$KEYCLOAK_ADMIN_PASSWORD" \
          --arg email_user "$EMAIL_USER" \
          --arg email_key "$EMAIL_KEY" \
          '{
              "bd.user": $bd_user,
              "bd.password": $bd_pass,
              "keycloak.admin.user": $k_user,
              "keycloak.admin.password": $k_pass,
              "email.user": $email_user,
              "email.key": $email_key
          }')
aws --endpoint-url=http://localstack:4566 secretsmanager create-secret --name /secret/car-rental --secret-string "$JSON"
