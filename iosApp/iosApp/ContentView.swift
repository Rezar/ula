import SwiftUI
import shared

import AVKit

//struct Home: View {
//    let setOpenLockGame: (Int) -> Void = { value in }
//
//    var body: some View {
//        HomeScreen(
//            setOpenLockGame: setOpenLockGame
//        )
//    }
//
//}
//
//struct Stats: View {
//
//    var body: some View {
//        StatsScreen()
//    }
//
//}
//
//struct Help: View {
//
//    var body: some View {
//        HelpListScreen()
//    }
//
//}
//
//struct Setting: View {
//
//    var body: some View {
//        SettingScreen()
//    }
//
//}
//
//struct Debug: View {
//
//    var body: some View {
//        DebugScreen()
//    }
//
//}

//@available(iOS 15.0, *)
struct AppScreen: View {
    @EnvironmentObject var healthManager: HealthManager
    
    @State private var isFirstTime = UserDefaults.standard.object(forKey: "isFirstTime") as? Bool ?? true
    @State private var welcomePageNumber = 0
    
    func nextPage() -> Void {
        welcomePageNumber += 1
    }
    
    func previousPage() -> Void {
        welcomePageNumber -= 1
    }
    
    var body: some View {
        if (isFirstTime) {
            switch welcomePageNumber {
                // Welcome Page 1
                case 0:
                    WelcomePage1(
                        onNextButtonClicked: {
                            nextPage()
                        }
                    )
                // Welcome Page 2
                case 1:
                    WelcomePage2(
                        onPreviousButtonClicked: {
                            previousPage()
                        },
                        onNextButtonClicked: {
                            nextPage()
                        }
                    )
                // Welcome Page 3
                case 2:
                    WelcomePage3(
                        onPreviousButtonClicked: {
                            previousPage()
                        },
                        onNextButtonClicked: {
                            UserDefaults.standard.set(false, forKey: "isFirstTime")
                            UserDefaults.standard.set(DateTimeUtil.getCurrentDateTime(), forKey: "SavedEffectiveDate")
                            isFirstTime = false
                        }
                    )
                default:
                    VStack{
                        Text("Error")
                    }
            }
            
            
        } else {
            GameScreen()
                .environmentObject(healthManager)
        }
        
        
    }
    
}


struct GameScreen: View {
    @EnvironmentObject var healthManager: HealthManager
    // TODO: Need a better solution for this. openLockGame is not a good name.
    // The value of openLockGame: [0, 4]
    // = 0: Not open any lock game. Stays in Game Screens
    // = 1: Open TicTacToe
    // = 2: Open Dino Game
    // = 3: Open Snake Game
    // = 4: Open Flappy Bird
    @State private var openLockGame = 0
    
    func setOpenLockGame(openLockGame: Int) -> Void {
        self.openLockGame = openLockGame
    }
    
    var body: some View {
        switch (openLockGame) {
            case 0:
                TabView{
                    HomeScreen(
                        setOpenLockGame: setOpenLockGame
                    )
                    .tabItem{
    //                    Text("Home")
                        Image("ic_ula")
                        .resizable()
                        .frame(width: 32.0, height: 32.0).clipped()
                    }
                
                    StatsScreen()
                    .tabItem{
    //                    Text("Stats")
                        Image("ic_stats")
                        .resizable()
                        .frame(width: 32.0, height: 32.0).clipped()
                    }
                    .environmentObject(healthManager)
                
                    HelpListScreen()
                    .tabItem{
    //                    Text("Help")
                        Image("ic_help")
                        .resizable()
                        .frame(width: 32.0, height: 32.0).clipped()
                    }
                
                    SettingScreen()
                    .tabItem{
    //                    Text("Setting")
                        Image("ic_cog")
                        .resizable()
                        .frame(width: 32.0, height: 32.0).clipped()
                    }
                
                    DebugScreen()
                    .tabItem{
    //                    Text("Debug")
                        Image("ic_debug")
                        .resizable()
                        .frame(width: 32.0, height: 32.0).clipped()
                    }
            }
            case 1:
                TicTacToe(
                    setOpenLockGame: setOpenLockGame
                )
            case 2:
                AsteroidTeilGameView(
                    setOpenLockGame: setOpenLockGame
                )
            case 3:
                TappyBird(
                    setOpenLockGame: setOpenLockGame
                )
            case 4:
                Breakout(
                    setOpenLockGame: setOpenLockGame
                )
            default:
                VStack{
                    Text("Error")
                }
        }
    }
    
    
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		GameScreen()
	}
}
