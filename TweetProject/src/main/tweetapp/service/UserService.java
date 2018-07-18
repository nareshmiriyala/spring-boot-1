package tweetapp.service;

import tweetapp.TweetApp;
import tweetapp.model.User;
import tweetapp.util.DateUtil;
import tweetapp.util.FileUtil;
import tweetapp.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserService {

  /**
   * Process user data and return list user
   * @param br
   * @return Return list user
   * @throws IOException
   */
  private static List<User> processUserData(BufferedReader br) throws IOException {
    String line;
    String csvSplitBy = ",";
    boolean headerRow = true;

    List<User> users = new ArrayList<>();

    while ((line = br.readLine()) != null) {

      // Ignore first line because it is header row
      if (headerRow) {
        headerRow = false;
        continue;
      }

      // Split line to array data
      String[] data = line.split(csvSplitBy);
      String id = StringUtil.substringBetween(data[0], "\"");
      String username = data[2];
      String firstName = data[3];
      String lastName = data[4];
      String avatarUrl = data[6];
      String nickname = data[7];
      String email = data[8];
      String phone = data[9];
      String address = data[10];
      String gender = data[13];
      LocalDateTime birthday = DateUtil.convertStringToLocalDateTime(data[14]);
      String description =  data[15];
      LocalDateTime createdAt = DateUtil.convertStringToLocalDateTime(data[16]);

      LocalDateTime modifiedAt = DateUtil.convertStringToLocalDateTime(data[17]);
      String version = data[18];

      // Create instance User
      User user = new User(id, username, firstName, lastName, avatarUrl, nickname, email, phone, address, gender, birthday,
          description, createdAt, modifiedAt, version);

      users.add(user);
    }
    return users;
  }

  /**
   * Get all list user from csv file
   * @return All info users
   * @throws IOException
   */
  public static List<User> getUsers() throws IOException {
    String csvFile ="./src/main/resources/users.csv";
    return FileUtil.readFile(csvFile, UserService::processUserData);
  }
}
