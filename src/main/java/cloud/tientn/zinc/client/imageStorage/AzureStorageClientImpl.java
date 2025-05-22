package cloud.tientn.zinc.client.imageStorage;

import cloud.tientn.zinc.exception.CustomBlobStorageException;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobStorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AzureStorageClientImpl implements ImageStorageClient{
    private final BlobServiceClient blobServiceClient;

    @Override
    public String uploadImage(String containerName, String originalName, InputStream data, Long length) {


        try{
            BlobContainerClient client =blobServiceClient.getBlobContainerClient(containerName);
            //create new name for image
            String newImageName=UUID.randomUUID().toString()+originalName.substring(originalName.lastIndexOf("."));
            BlobClient blobClient =client.getBlobClient(newImageName);
            blobClient.upload(data, length, true);

            //return blobClient.getBlobUrl();
            return newImageName;
        }catch (BlobStorageException e){
            throw new CustomBlobStorageException("Failed to upload image ", e);
        }

    }
}
