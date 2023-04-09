package search;

import java.util.List;

public class SearchEngineResult {
    String engineName;
    List<Item> urls;

    public String getEngineName() {
        return engineName;
    }

    public List<Item> getUrls() {
        return urls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchEngineResult that = (SearchEngineResult) o;

        if (urls.size() != that.urls.size()) {
            return false;
        }
        for (int i = 0; i < urls.size(); i++) {
            if (!urls.get(i).equals(that.urls.get(i))) {
                return false;
            }
        }
        return engineName.equals(that.engineName);
    }

    public static class Item {
        String title;
        String url;

        public Item(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return title.equals(item.title) && url.equals(item.url);
        }

    }
}
