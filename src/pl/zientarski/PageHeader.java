package pl.zientarski;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class PageHeader {
    private final String capturePattern;
    private final byte streamStructureVersion;
    private final boolean freshPacket;
    private final boolean firstPage;
    private final boolean lastPage;
    private final long absoluteGranulePosition;
    private final int streamSerialNumber;
    private final int pageSequenceNumber;
    private final int checksum;
    private final byte segments;
    private final byte[] segmentTable;
    private final int payloadSize;

    public PageHeader(final InputStream inputStream) throws IOException {
        final byte[] bytes = new byte[27];
        inputStream.read(bytes);
        capturePattern = new String(bytes, 0, 4, "ASCII");
        streamStructureVersion = bytes[4];
        freshPacket = (bytes[5] & 0b00000001) == 0;
        firstPage = (bytes[5] & 0b00000010) != 0;
        lastPage = (bytes[5] & 0b00000100) != 0;
        absoluteGranulePosition = bytes[6] +
                (bytes[7] << 8) +
                (bytes[8] << 16) +
                (bytes[9] << 24) +
                (bytes[10] << 32) +
                (bytes[11] << 40) +
                (bytes[12] << 48) +
                (bytes[13] << 56);
        streamSerialNumber = bytes[14] +
                (bytes[15] << 8) +
                (bytes[16] << 16) +
                (bytes[17] << 24);
        pageSequenceNumber = bytes[18] +
                (bytes[19] << 8) +
                (bytes[20] << 16) +
                (bytes[21] << 24);
        checksum = bytes[22] +
                (bytes[23] << 8) +
                (bytes[24] << 16) +
                (bytes[25] << 24);
        segments = bytes[26];
        segmentTable = new byte[(segments + 256) % 256];
        inputStream.read(segmentTable);

        int size = 0;
        for (int i = 0; i < segments; i++) {
            size += (segmentTable[i] + 256) % 256;
        }
        payloadSize = size;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    @Override
    public String toString() {
        return "PageHeader{" +
                "capturePattern='" + capturePattern + '\'' +
                ", streamStructureVersion=" + streamStructureVersion +
                ", freshPacket=" + freshPacket +
                ", firstPage=" + firstPage +
                ", lastPage=" + lastPage +
                ", absoluteGranulePosition=" + absoluteGranulePosition +
                ", streamSerialNumber=" + streamSerialNumber +
                ", pageSequenceNumber=" + pageSequenceNumber +
                ", checksum=" + checksum +
                ", segments=" + segments +
                ", segmentTable=" + Arrays.toString(segmentTable) +
                ", payloadSize=" + payloadSize +
                '}';
    }
}
