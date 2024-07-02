ALTER TABLE group_sub
    CHANGE COLUMN last_article_id last_post_id INT;

ALTER TABLE tg_user
    MODIFY chat_id INT;
