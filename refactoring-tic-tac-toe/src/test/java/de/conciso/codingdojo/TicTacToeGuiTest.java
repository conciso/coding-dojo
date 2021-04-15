package de.conciso.codingdojo;

import static com.google.common.truth.Truth.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@ExtendWith(EasyMockExtension.class)
public class TicTacToeGuiTest {

  @Mock
  private TicTacToe ticTacToeMock;

  @TestSubject
  private TicTacToeGui cut = new TicTacToeGuiImpl(ticTacToeMock);


  @Test
  void whenGameIsStartedShouldAskForPlayer1Name() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(byteArrayOutputStream);
    System.setOut(ps);

    cut.start();
    assertThat(byteArrayOutputStream.toString()).isEqualTo("Player1 name: ");
  }

  @Test
  void whenGameIsStartedShouldSetPlayer1Name() {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Max Mustermann\n".getBytes());
    System.setIn(byteArrayInputStream);

    ticTacToeMock.setPlayer1Name("Max Mustermann");
    EasyMock.replay(ticTacToeMock);

    cut.start();
    EasyMock.verify(ticTacToeMock);
  }

  @Test
  void whenGameIsStartedShouldAskForPlayer2Name() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(byteArrayOutputStream);
    System.setOut(ps);

    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Name\n".getBytes());
    System.setIn(byteArrayInputStream);
    cut.start();

    assertThat(byteArrayOutputStream.toString()).contains("Player2 name: ");
  }

  @Test
  void whenGameIsStartedShouldSetPlayer2Name() {

  }


}
