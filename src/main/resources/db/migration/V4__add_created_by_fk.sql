ALTER TABLE short_urls ADD COLUMN created_by BIGINT;

-- backfill or cleanup old rows
DELETE FROM short_urls WHERE created_by IS NULL;

ALTER TABLE short_urls ALTER COLUMN created_by SET NOT NULL;

ALTER TABLE short_urls
    ADD CONSTRAINT fk_short_urls_user
    FOREIGN KEY (created_by) REFERENCES users(id);
