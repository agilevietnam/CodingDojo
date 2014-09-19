
keepPlaying = "y"

def game = new Game();
// play as many games as user would like

while (keepPlaying == "y")
{
    game.play()

    keepPlaying = System.console().readLine("\nThat was fun. Play again? (y/n): ")
    keepPlaying = keepPlaying.toLowerCase()

    println "\n"
}