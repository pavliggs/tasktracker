package ru.progwards.tasktracker.service.vo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Файл-вложение, прикрепленный к задаче, или еще куда-нибудь в будущем
 *
 * Бизнес-объект предназначен для сохранения в репозиторий и для передачи пользователю
 * Для отображения в интерфейсе используйте TaskAttachment
 *
 * @author Gregory Lobkov
 */
public class AttachmentContent {


    private Long id;

    /**
     * Содержимое вложения
     */
    private InputStream data;


    public AttachmentContent(Long id, InputStream data) {
        this.id = id;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InputStream getData() {
        return data;
    }

    public void setData(InputStream data) {
        this.data = data;
    }

}