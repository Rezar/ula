import SwiftUI
import shared

struct Home: View {
	let greet = Greeting().greet()

	var body: some View {
		Text(greet)
	}
    
}

struct Stats: View {

    var body: some View {
        Text("Stats")
    }
    
}

struct Help: View {

    var body: some View {
        Text("Help")
    }
    
}

struct Setting: View {

    var body: some View {
        Text("Setting")
    }
    
}

struct Debug: View {

    var body: some View {
        Text("Debug")
    }
    
}

struct GameScreen: View {
    
    var body: some View {
        TabView{
            Home()
                .tabItem{
                    Text("Home")
                    Image("ic_ula").resizable().frame(width: 32.0, height: 32.0).clipped()
                }
            
            Stats()
                .tabItem{
                    Text("Stats")
                    Image("ic_stats").resizable().frame(width: 32.0, height: 32.0).clipped()
                }
            
            Help()
                .tabItem{
                    Text("Help")
                    Image("ic_help").resizable().frame(width: 32.0, height: 32.0).clipped()
                }
            
            Setting()
                .tabItem{
                    Text("Setting")
                    Image("ic_cog").resizable().frame(width: 32.0, height: 32.0).clipped()
                }
            
            Debug()
                .tabItem{
                    Text("Debug")
                    Image("ic_debug").resizable().frame(width: 32.0, height: 32.0).clipped()
                }
        }
    }
    
}




//struct ContentView_Previews: PreviewProvider {
//	static var previews: some View {
//		GameScreen()
//	}
//}
