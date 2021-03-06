package wykopapi.api.request.entries;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.VoteEntry;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class VoteEntryCommentRequest implements ApiRequest<VoteEntry> {
    private final String userKey;
    private final int entryId;
    private final int commentId;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("entries", "vote")
                .addMethodParam("comment")
                .addMethodParam(String.valueOf(entryId))
                .addMethodParam(String.valueOf(commentId))
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return VoteEntry.class;
    }

    public static VoteEntryCommentRequestBuilder builder(@NotNull String userKey, int entryId, int commentId) {
        return new VoteEntryCommentRequestBuilder(userKey, entryId, commentId);
    }

    public static class VoteEntryCommentRequestBuilder {
        private String userKey;
        private int entryId;
        private int commentId;

        private VoteEntryCommentRequestBuilder(@NotNull String userKey, int entryId, int commentId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (entryId < 0 || commentId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.entryId = entryId;
            this.commentId = commentId;
        }

        public VoteEntryCommentRequest build() {
            return new VoteEntryCommentRequest(userKey, entryId, commentId);
        }
    }
}
