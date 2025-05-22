package cloud.tientn.zinc.client.imageStorage;

import java.io.InputStream;

public interface ImageStorageClient {
    String uploadImage(String containerName,String originalName, InputStream data, Long length);

}
