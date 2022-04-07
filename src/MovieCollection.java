import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.*;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }
  
  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();
    
    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();
    
    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();
    
    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();
      
      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }
    
    // sort the results by title
    sortResults(results);
    
    // now, display them all to the user    
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();
      
      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;
      
      System.out.println("" + choiceNum + ". " + title);
    }
    
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    
    int choice = scanner.nextInt();
    scanner.nextLine();
    
    Movie selectedMovie = results.get(choice - 1);
    
    displayMovieInfo(selectedMovie);
    
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();
      
      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortList(ArrayList<String> words)
  {
    for (int j = 1; j < words.size(); j++)
    {
      String temp = words.get(j);
      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(words.get(possibleIndex - 1)) < 0)
      {
        words.set(possibleIndex, words.get(possibleIndex - 1));
        possibleIndex--;
      }
      words.set(possibleIndex, temp);
    }

  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
    System.out.println("Please enter the cast member: ");
    String member = scanner.nextLine();
    member = member.toLowerCase();
    ArrayList<String> cast = new ArrayList<String>();
    //looks for cast member and adds to arraylist cast
    for (int i = 0; i < movies.size(); i++)
    {
      String[] tempMovie = movies.get(i).getCast().split("\\|");
      for (int j = 0; j < tempMovie.length; j++)
      {
        String name = tempMovie[j].toLowerCase();
        //checks for a match in the first name
        if (((name.substring(0, name.indexOf(" ") + 1).contains(member))))
        {
          //checks for duplicates
          if (!(cast.contains(tempMovie[j])))
          {
            cast.add(tempMovie[j]);
          }
        }
      }
    }
    //System.out.println(cast); - used as a test to print out the cast for correct names
    //sort the cast in alphabetical order
    sortList(cast);
    for (int i = 0; i < cast.size(); i++)
    {
      int index = 1 + i;
      System.out.println(index + ". " + cast.get(i));
    }
    ArrayList<Movie> info = new ArrayList<Movie>();
    System.out.println("Which cast member would you like to look up?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    int userChoice = choice-1;
    int index = 1;
    //displays list of movies
    for (int i = 0; i < movies.size(); i++)
    {
      if (movies.get(i).getCast().contains(cast.get(userChoice)))
      {
        System.out.println(index + ". " + movies.get(i).getTitle());
        info.add(movies.get(i));
        index++;
      }
    }
    System.out.println("Which movie would you like to learn about?");
    System.out.print("Enter number: ");
    int movieChoice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = info.get(movieChoice-1);
    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void searchKeywords()
  {
    System.out.println("Please enter a keyword: ");
    String key = scanner.nextLine();
    key = key.toLowerCase();
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String word = movies.get(i).getKeywords();
      word = word.toLowerCase();

      if (word.indexOf(key) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }


  private void listGenres()
  {
    ArrayList<String> genres = new ArrayList<String>();
    for (int i = 0; i < movies.size(); i++)
    {
      String[] tempMovie = movies.get(i).getGenres().split("\\|");
      //System.out.println(Arrays.toString(tempMovie));
      for (int j = 0; j < tempMovie.length; j++)
      {
        if (!(genres.contains(tempMovie[j])))
        {
          genres.add(tempMovie[j]);
        }
      }

    }
    //.out.println(genres);
    sortList(genres);
    for (int i = 0; i < genres.size(); i++)
    {
      int index = 1 + i;
      System.out.println(index + ". " + genres.get(i));
    }
    ArrayList<Movie> info = new ArrayList<Movie>();
    System.out.println("Which genre would you like to search?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    //scanner.nextLine();
    int userChoice = choice-1;
    int index = 1;
    //displays list of movies
    for (int i = 0; i < movies.size(); i++)
    {
      if (movies.get(i).getGenres().equals(genres.get(userChoice)))
      {
        System.out.println(index + ". " + movies.get(i).getTitle());
        info.add(movies.get(i));
        index++;
      }
    }
    System.out.println("Which movie would you like to learn about?");
    System.out.print("Enter number: ");
    int movieChoice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = info.get(movieChoice-1);
    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listHighestRated()
  {
    ArrayList<Movie> topMovies = new ArrayList<Movie>();
    for (int i = 0; i < movies.size(); i++)
    {
      topMovies.add(movies.get(i));
    }
    for (int i = 0; i < topMovies.size(); i++)
    {
      int index1 = i;
      for (int j = i + 1; j < topMovies.size(); j++)
      {
        if (topMovies.get(j).getUserRating() < topMovies.get(i).getUserRating())
        {
          index1 = j;
        }
      }
      Movie temp = topMovies.get(i);
      topMovies.set(i, topMovies.get(index1));
      topMovies.set(index1, temp);
    }
    int movieNumber = 0;
    ArrayList<Movie> movieList = new ArrayList<Movie>();
    //prints out sorted list of movies and adds them into another arraylist
    for (int i = topMovies.size()-1; i >= 4949; i--)
    {
      movieNumber += 1;
      movieList.add(topMovies.get(i));
      System.out.println(movieNumber + ". " + topMovies.get(i).getTitle() + " Rating - " + topMovies.get(i).getUserRating());
    }
    System.out.println("Which movie would you like to learn about?");
    System.out.print("Enter number: ");
    int movieChoice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = movieList.get(movieChoice-1);
    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listHighestRevenue()
  {
    ArrayList<Movie> topMovies = new ArrayList<Movie>();
    for (int i = 0; i < movies.size(); i++)
    {
      topMovies.add(movies.get(i));
    }
    for (int i = 0; i < topMovies.size(); i++)
    {
      int index1 = i;
      for (int j = i + 1; j < topMovies.size(); j++)
      {
        if (topMovies.get(j).getRevenue() < topMovies.get(i).getRevenue())
        {
          index1 = j;
        }
      }
      Movie temp = topMovies.get(i);
      topMovies.set(i, topMovies.get(index1));
      topMovies.set(index1, temp);
    }
    int movieNumber = 0;
    ArrayList<Movie> movieList = new ArrayList<Movie>();
    //prints out sorted list of movies and adds them into another arraylist
    for (int i = topMovies.size()-1; i >= 4949; i--)
    {
      movieNumber += 1;
      movieList.add(topMovies.get(i));
      System.out.println(movieNumber + ". " + topMovies.get(i).getTitle() + " Revenue - " + topMovies.get(i).getRevenue());
    }
    System.out.println("Which movie would you like to learn about?");
    System.out.print("Enter number: ");
    int movieChoice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = movieList.get(movieChoice-1);
    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();
      
      movies = new ArrayList<Movie>();
      //wdadauihawidh
      while ((line = bufferedReader.readLine()) != null) 
      {
        String[] movieFromCSV = line.split(",");
     
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);
        
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
        movies.add(nextMovie);  
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());              
    }
  }
}