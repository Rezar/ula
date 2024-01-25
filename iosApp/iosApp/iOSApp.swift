import SwiftUI

@main
struct iOSApp: App {
    @StateObject var healthManager = HealthManager()
    
	var body: some Scene {
		WindowGroup {
            AppScreen()
                .environmentObject(healthManager)
		}
	}
}
