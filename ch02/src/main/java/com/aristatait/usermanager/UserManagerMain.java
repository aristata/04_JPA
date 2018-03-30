package com.aristatait.usermanager;

import com.aristatait.configuration.CustomEntityMangerFactory;
import com.aristatait.model.User;
import com.aristatait.usermanager.service.ChangeNameService;
import com.aristatait.usermanager.exception.DuplicateEmailException;
import com.aristatait.usermanager.exception.UserNotFoundException;
import com.aristatait.usermanager.service.FindService;
import com.aristatait.usermanager.service.JoinService;
import com.aristatait.usermanager.service.WithdrawService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserManagerMain {

  // Join Service
  private static JoinService joinService = new JoinService();

  // Find Service
  private static FindService findService = new FindService();

  // Change name Service
  private static ChangeNameService changeNameService = new ChangeNameService();

  // Withdraw Service
  private static WithdrawService withdrawService = new WithdrawService();

  public static void main(String[] args) throws IOException {

    // EntityManagerFactory 생성
    CustomEntityMangerFactory.init();

    // 콘솔에서 명령어를 입력받기 위한 BufferedReader를 생성
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    while(true) {
      System.out.println("명령어를 입력하세요.");
      System.out.print(": ");

      // 사용자가 입력한 문자열을 변수에 저장
      String line = bufferedReader.readLine();

      // 입력받은 문자열을 공백문자로 분리한다.
      String[] commands = line.split(" ");

      if (commands[0].equalsIgnoreCase("exit")) {
        System.out.println("종료합니다.");
        break;
      } else if (commands[0].equalsIgnoreCase("join")) {
        // 사용자 등록
        handleJoinCommand(commands);
      } else if (commands[0].equalsIgnoreCase("view")) {
        // 사용자 조회
        handleViewCommand(commands);
      } else if (commands[0].equalsIgnoreCase("list")) {
        // 사용자 목록 조회
        handleListCommand();
      } else if (commands[0].equalsIgnoreCase("changename")) {
        // 사용자 이름 변경
        handleChangeNameCommand(commands);
      } else if (commands[0].equalsIgnoreCase("withdraw")) {
        handleWithdrawCommand(commands);
      } else {
        System.out.println("올바른 명령어를 입력하세요.");
      }
      System.out.println("----");

    } // End of while

    CustomEntityMangerFactory.close();
  }

  /**
   *  Join 명령어가 입력 되었을때의 처리
   */
  private static void handleJoinCommand(String[] cmd) {

    // 입력한 명령어의 인자 개수가 맞지 않으면 사용법을 출력하고 메소드를 리턴한다.
    if (cmd.length != 3) {
      System.out.println("명령어가 올바르지 않습니다.");
      System.out.println("사용법: join 이메일 이름");
      return;
    }

    try {

      // 가입을 처리한다.
      joinService.join( new User( cmd[1], cmd[2], new Date() ) );

      System.out.println("가입 요청을 처리했습니다.");

    } catch (DuplicateEmailException e) {
      System.out.println("이미 같은 이메일을 가진 사용자가 존재합니다.");
    }
  }

  /**
   *  View 명령어를 입력 받았을때의 처리
   */
  private static void handleViewCommand(String[] cmd) {

    if (cmd.length != 2) {
      System.out.println("명령어가 올바르지 않습니다.");
      System.out.println("사용법: view 이메일");
      return;
    }

    Optional<User> userOptional = findService.findUser(cmd[1]);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      System.out.println("이름: " + user.getName());
      System.out.printf("생성: %tY년 %<tm월 %<td일\n", user.getCreateDate());
    } else {
      System.out.println("존재하지 않습니다.");
    }
  }

  /**
   *  list 명령어를 입력 받았을때의 처리
   */
  private static void handleListCommand() {

    List<User> users = findService.findAll();

    if (users.isEmpty()) {
      System.out.println("사용자가 없습니다.");
    } else {
      users.forEach(user ->
        System.out.printf(
          "| %s | %s | %tY년 %<tm월 %<td일 |\n",
          user.getEmail(), user.getName(), user.getCreateDate()
        )
      );
    }
  }

  /**
   *  ChangeName 명령어를 입력 받았을때의 처리
   */
  private static void handleChangeNameCommand(String[] cmd) {

    if (cmd.length != 3) {
      System.out.println("명령어가 올바르지 않습니다.");
      System.out.println("사용법: changename 이메일 새이름");
      return;
    }

    try {
      changeNameService.changeName(cmd[1], cmd[2]);
      System.out.println("이름을 변경했습니다.");
    } catch (UserNotFoundException ex) {
      System.out.println("존재하지 않습니다.");
    }
  }

  /**
   *  Withdraw 명령어를 입력 받았을때의 처리
   */
  private static void handleWithdrawCommand(String[] cmd) {

    if (cmd.length != 2) {
      System.out.println("명령어가 올바르지 않습니다.");
      System.out.println("사용법: withdraw 이메일");
      return;
    }

    try {

      withdrawService.withdraw(cmd[1]);
      System.out.println("탈퇴처리 했습니다.");

    } catch (UserNotFoundException e) {

      System.out.println("존재하지 않습니다.");
    }
  }
}
