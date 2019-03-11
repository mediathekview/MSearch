package de.mediathekview.mlib.daten;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A class that holds a thread safe map of {@link Film} with there UUIDs and some additional
 * information.
 */
public class Filmlist {
  private final ConcurrentHashMap<UUID, Film> films;
  private final ConcurrentHashMap<UUID, Podcast> podcasts;
  private final ConcurrentHashMap<UUID, Livestream> livestreams;
  private LocalDateTime creationDate;
  private UUID listId;

  public Filmlist() {
    this(UUID.randomUUID(), LocalDateTime.now());
  }

  public Filmlist(final UUID aListId, final LocalDateTime aCreationDate) {
    super();
    films = new ConcurrentHashMap<>();
    podcasts = new ConcurrentHashMap<>();
    livestreams = new ConcurrentHashMap<>();
    listId = aListId;
    creationDate = aCreationDate;
  }

  public void add(final Film aFilm) {
    films.put(aFilm.getUuid(), aFilm);
  }

  public void add(final Livestream aLivestream) {
    livestreams.put(aLivestream.getUuid(), aLivestream);
  }

  public void add(final Podcast aPodcast) {
    podcasts.put(aPodcast.getUuid(), aPodcast);
  }

  public void addAllFilms(final Collection<Film> aFilms) {
    aFilms.stream().forEach(this::add);
  }

  public void addAllLivestreams(final Collection<Livestream> aLivestreams) {
    aLivestreams.stream().forEach(this::add);
  }

  public void addAllPodcasts(final Collection<Podcast> aPodcasts) {
    aPodcasts.stream().forEach(this::add);
  }


  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Filmlist other = (Filmlist) obj;
    if (creationDate == null) {
      if (other.creationDate != null) {
        return false;
      }
    } else if (!creationDate.equals(other.creationDate)) {
      return false;
    }
    if (films == null) {
      if (other.films != null) {
        return false;
      }
    } else if (!films.equals(other.films)) {
      return false;
    }
    if (listId == null) {
      if (other.listId != null) {
        return false;
      }
    } else if (!listId.equals(other.listId)) {
      return false;
    }
    if (livestreams == null) {
      if (other.livestreams != null) {
        return false;
      }
    } else if (!livestreams.equals(other.livestreams)) {
      return false;
    }
    if (podcasts == null) {
      if (other.podcasts != null) {
        return false;
      }
    } else if (!podcasts.equals(other.podcasts)) {
      return false;
    }
    return true;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public ConcurrentMap<UUID, Film> getFilms() {
    return films;
  }


  public List<Film> getFilmsSorted(
      final Comparator<AbstractMediaResource<?>> aComparator) {
    final List<Film> sortedFilms = new ArrayList<>(films.values());
    sortedFilms.sort(aComparator);
    return sortedFilms;
  }

  public UUID getListId() {
    return listId;
  }

  public ConcurrentMap<UUID, Livestream> getLivestreams() {
    return livestreams;
  }

  public List<Livestream> getLivestreamsSorted(
      final Comparator<AbstractMediaResource<?>> aComperator) {
    final List<Livestream> sortedLivestreams = new ArrayList<>(livestreams.values());
    sortedLivestreams.sort(aComperator);
    return sortedLivestreams;
  }

  public ConcurrentMap<UUID, Podcast> getPodcasts() {
    return podcasts;
  }

  public List<Podcast> getPodcastsSorted(final Comparator<AbstractMediaResource<?>> aComperator) {
    final List<Podcast> sortedPodcasts = new ArrayList<>(podcasts.values());
    sortedPodcasts.sort(aComperator);
    return sortedPodcasts;
  }

  public List<AbstractMediaResource<?>> getSorted(
      final Comparator<AbstractMediaResource<?>> aComperator) {
    final List<AbstractMediaResource<?>> sortedResources = new ArrayList<>(films.values());
    sortedResources.addAll(podcasts.values());
    sortedResources.addAll(livestreams.values());
    sortedResources.sort(aComperator);
    return sortedResources;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (creationDate == null ? 0 : creationDate.hashCode());
    result = prime * result + (films == null ? 0 : films.hashCode());
    result = prime * result + (listId == null ? 0 : listId.hashCode());
    result = prime * result + (livestreams == null ? 0 : livestreams.hashCode());
    result = prime * result + (podcasts == null ? 0 : podcasts.hashCode());
    return result;
  }

  /**
   * Merges this film list with the given film list and returns the difference list.
   *
   * @param aFilmlist The film list to merge into this.
   * @return The difference list.
   */
  public Filmlist merge(final Filmlist aFilmlist) {
    final Filmlist differenceList = new Filmlist(UUID.randomUUID(), creationDate);

    aFilmlist.films.entrySet().stream().filter(e -> !films.containsKey(e.getKey()))
        .forEachOrdered(e -> differenceList.films.put(e.getKey(), e.getValue()));

    aFilmlist.podcasts.entrySet().stream().filter(e -> !podcasts.containsKey(e.getKey()))
        .forEachOrdered(e -> differenceList.podcasts.put(e.getKey(), e.getValue()));

    aFilmlist.livestreams.entrySet().stream().filter(e -> !livestreams.containsKey(e.getKey()))
        .forEachOrdered(e -> differenceList.livestreams.put(e.getKey(), e.getValue()));

    films.putAll(differenceList.films);
    podcasts.putAll(differenceList.podcasts);
    livestreams.putAll(differenceList.livestreams);

    return differenceList;
  }

  public void setCreationDate(final LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public void setListId(final UUID listId) {
    this.listId = listId;
  }
}