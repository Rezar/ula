//
//  StatScreen.swift
//  iosApp
//
//  Created by Hao Wu on 9/5/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Charts
import SwiftUI
import shared


struct StatsScreen: View {
    
    @ObservedObject var statViewModel: IOSStatViewModel
//    @State private var showDetail = false
    
    init() {
        self.statViewModel = IOSStatViewModel()
        
    }
    
    var body: some View {
        
        NavigationView {
            
            VStack(alignment: .leading, spacing: 20.0) {
                
                // Title
                Text("Summary")
                    .font(.title)
                    .foregroundStyle(.black)
                
                // Card content
                let stepHistory = statViewModel.readStepHistory()
                NavigationLink(destination: StatDetailScreen(stepHistory: stepHistory)) {
                    VStack(alignment: .leading, spacing: 20.0) {
                        
                        HStack {
                            Text("Steps").foregroundStyle(.black)
                            
                            Spacer()
                            
                            Text("Fitness Detail >").foregroundStyle(.black) // Direct to screen that shows fitness detail
                        }
                        
                        
                        HStack(alignment: .bottom) {
                            Text("Sensor").font(.system(size: 30)).foregroundStyle(.black)   // TODO: Read steps from sensor
                            Text("steps").foregroundStyle(.black)
                        }
                        
                    }
                    .padding()
                    .background(Rectangle()
                        .foregroundColor(.white)
                        .cornerRadius(5)
                        .shadow(radius: 5)
                    )
                }
                
                // Histogram to display the steps
                VStack(alignment: .leading, spacing: 20.0) {
                    Chart {
                        // TODO: Change the goal to importing from settingViewModel
                        RuleMark(y: .value("Goal", statViewModel.goal))
                            .foregroundStyle(Color.black)
                            .lineStyle(StrokeStyle(lineWidth: 1, dash: [5]))
                            .annotation(alignment: .leading) {
                                Text("Goal")
                                    .font(.caption).foregroundColor(.secondary)
                            }
                        ForEach(stepHistory) { steps in
                            BarMark(
                                x: .value("Date", steps.date, unit: .day),
                                y: .value("Steps", steps.stepCount)
                            )
                            .foregroundStyle(Color.pink.gradient)
                            .cornerRadius(10)
                        }
                    }.frame(height: 180)
                }
                .padding()
                .background(Rectangle()
                    .foregroundColor(.white)
                    .cornerRadius(5)
                    .shadow(radius: 5)
                )
                
                Spacer()
                
            }
            .padding()
            
        }
        
    }
}




struct StatScreen_Previews: PreviewProvider {
    static var previews: some View {
        StatsScreen()
    }
}
