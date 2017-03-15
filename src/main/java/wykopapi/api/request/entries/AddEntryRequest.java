package wykopapi.api.request.entries;

import com.google.common.base.Strings;
import okhttp3.*;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;
import wykopapi.api.dto.EntryOperation;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class AddEntryRequest extends AbstractRequest<EntryOperation> {
    private final String userKey;
    private final String body;
    private final String embedUrl;
    private final File embedFile;

    private AddEntryRequest(String userKey, String body, String embedUrl, File embedFile) {
        this.userKey = userKey;
        this.body = body;
        this.embedUrl = embedUrl;
        this.embedFile = embedFile;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("add")
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("body", body);
        if (!Strings.isNullOrEmpty(embedUrl)) parameters.put("embed", embedUrl);

        RequestBody requestBody = embedFile == null
                ? createBodyFromParams(parameters)
                : createMultipartBody(parameters, embedFile);

        return new Request.Builder()
                .url(url).post(requestBody)
                .build();
    }

    @Override
    public Type getReturnType() {
        return EntryOperation.class;
    }

    public static class Builder implements ApiRequestBuilder<AddEntryRequest> {
        private String userKey;
        private String body;
        private String embedUrl;
        private File embedFile;

        public Builder(String userKey, String body) {
            this.body = body;
            this.userKey = userKey;
        }

        public Builder setEmbedUrl(String embedUrl) {
            this.embedUrl = embedUrl;
            this.embedFile = null;
            return this;
        }

        public Builder setEmbedFile(File embedFile) {
            this.embedFile = embedFile;
            this.embedUrl = null;
            return this;
        }

        public AddEntryRequest build() {
            return new AddEntryRequest(userKey, body, embedUrl, embedFile);
        }
    }
}