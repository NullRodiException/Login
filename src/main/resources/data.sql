-- Habilita a extensão necessária para gerar UUIDs e hashes bcrypt
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Versão completa (use esta se as colunas abaixo já existirem na sua tabela):
-- role: por padrão JPA sem @Enumerated(EnumType.STRING) salva ORDINAL (ADMIN = 0).
-- Se você estiver usando Enum como STRING, troque 0 por 'ADMIN'.
-- disabled: false
-- created_at / updated_at: timestamp atual
INSERT INTO users (id, name, username, email, hashed_password, role, created_at, updated_at)
SELECT gen_random_uuid(),
        'Administrator',
        'admin',
        'admin@example.com',
        crypt('admin123', gen_salt('bf')),
        'ADMIN',
        now(),
        now()
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@example.com' OR username = 'admin'
);

