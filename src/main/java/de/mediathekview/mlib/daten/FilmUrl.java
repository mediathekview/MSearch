package de.mediathekview.mlib.daten;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class FilmUrl implements Serializable {
  private static final long serialVersionUID = 576534421232286643L;
  private static final Logger LOG = LogManager.getLogger(FilmUrl.class);
  private static final String URL_START_NRODL = "//nrodl";
  private static final String URL_START_RODL = "//rodl";
  private static final String URL_HTTPS = "https";
  private static final String URL_HTTP = "http";
  private static final String NO_PROTOCOL = "//";
  private static final String HTTPS_PROTOCOL_PREFIX = URL_HTTPS + ":";
  private URL url;
  /** The file size in MiB. */
  private Long fileSize;

  public FilmUrl(final String url, final Long aFileSize) throws MalformedURLException {
    this(buildURL(url), aFileSize);
  }

  public FilmUrl(final URL url, final Long fileSize) {
    this.url = url;
    this.fileSize = fileSize;
  }

  @NotNull
  private static URL buildURL(final String url) throws MalformedURLException {
    return new URL(url.startsWith(NO_PROTOCOL) ? HTTPS_PROTOCOL_PREFIX + url : url);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof FilmUrl)) {
      return false;
    }
    final FilmUrl other = (FilmUrl) obj;
    return Objects.equals(fileSize, other.fileSize)
        && Objects.equals(makeUrlComparable(url), makeUrlComparable(other.url));
  }

  public Long getFileSize() {
    return fileSize;
  }

  public void setFileSize(final Long fileSize) {
    this.fileSize = fileSize;
  }

  public URL getUrl() {
    return url;
  }

  public void setUrl(final URL url) {
    this.url = url;
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileSize, makeUrlComparable(url));
  }

  private URL makeUrlComparable(final URL aUrl) {
    URL newUrl;
    if (aUrl == null) {
      newUrl = aUrl;
    } else {
      final String urlAsText = aUrl.toString();
      try {
        newUrl =
            new URL(
                urlAsText.replace(URL_START_NRODL, URL_START_RODL).replace(URL_HTTPS, URL_HTTP));
      } catch (final MalformedURLException aMalformedURLException) {
        LOG.fatal(
            "Can't replace the nrodl in these URL: " + aUrl.toString(), aMalformedURLException);
        newUrl = aUrl;
      }
    }
    return newUrl;
  }

  @Override
  public String toString() {
    return getUrl().toString();
  }
}