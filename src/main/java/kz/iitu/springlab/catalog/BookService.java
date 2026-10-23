package kz.iitu.springlab.catalog;

import org.springframework.stereotype.Service;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    // every book when author is null, otherwise a case-insensitive "contains" filter
    public List<Book> findAll(String author) {
        if (author == null || author.isBlank()) {
            return repository.findAll();
        }
        String needle = author.toLowerCase();
        return repository.findAll().stream()
                .filter(b -> b.author().toLowerCase().contains(needle))
                .toList();
    }

    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    public Book create(Book book) {
        return repository.save(new Book(null, book.title(), book.author(), book.year()));
    }

    // replace: empty Optional when there is no such book
    public Optional<Book> update(long id, Book book) {
        if (repository.findById(id).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(repository.save(new Book(id, book.title(), book.author(), book.year())));
    }

    public boolean delete(long id) {
        return repository.deleteById(id);
    }

    // individual assignment, variant 5
    public BookStats stats() {
        List<Book> all = repository.findAll();
        if (all.isEmpty()) {
            return new BookStats(0, null, null);
        }
        IntSummaryStatistics years = all.stream().mapToInt(Book::year).summaryStatistics();
        return new BookStats(all.size(), years.getMin(), years.getMax());
    }
}
