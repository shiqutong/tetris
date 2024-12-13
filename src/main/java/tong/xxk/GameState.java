package tong.xxk;

public enum GameState {
    START {
        @Override
        public void enter(TetrisBoard game) {
            System.out.println("Game state changed to: START");
        }

        @Override
        public void handleInput(TetrisBoard game, String input) {
            if ("start".equalsIgnoreCase(input)) {
                game.setState(RUNNING);
            }
        }
    },
    RUNNING {
        @Override
        public void enter(TetrisBoard game) {
            System.out.println("Game state changed to: RUNNING");
        }

        @Override
        public void handleInput(TetrisBoard game, String input) {
            switch (input.toLowerCase()) {
                case "pause":
                    game.setState(PAUSED);
                    break;
                case "end":
                    game.setState(GAME_OVER);
                    break;
            }
        }
    },
    PAUSED {
        @Override
        public void enter(TetrisBoard game) {
            System.out.println("Game state changed to: PAUSED");
        }

        @Override
        public void handleInput(TetrisBoard game, String input) {
            if ("resume".equalsIgnoreCase(input)) {
                game.setState(RUNNING);
            }
        }
    },
    GAME_OVER {
        @Override
        public void enter(TetrisBoard game) {
            System.out.println("Game state changed to: GAME_OVER");
        }

        @Override
        public void handleInput(TetrisBoard game, String input) {
            // No further actions in GAME_OVER state
        }
    };

    // Method to execute when entering this state
    public abstract void enter(TetrisBoard game);

    // Method to handle input in this state
    public abstract void handleInput(TetrisBoard game, String input);
}
