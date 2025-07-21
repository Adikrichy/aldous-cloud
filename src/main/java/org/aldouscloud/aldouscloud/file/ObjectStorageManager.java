package org.aldouscloud.aldouscloud.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ObjectStorageManager {
    /**
     * Сохраняет файл в файловую систему и возвращает полный путь к сохранённому файлу.
     *
     * @param file Multipart-файл, полученный от пользователя.
     * @param bucketName Название бакета (используется как часть пути).
     * @param objectKey Уникальный ключ объекта (имя файла).
     * @return Полный путь к сохранённому файлу.
     * @throws IOException Если что-то пошло не так при сохранении.
     */
    String save(MultipartFile file,String bucketName, String objectKey) throws IOException;
}
