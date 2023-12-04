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
import CoreMotion


struct StatsScreen: View {
    
    @State private var steps: Int = 0
    
    private let pedometer: CMPedometer = CMPedometer()
    
    private var isPedometerAvailable: Bool {
        return CMPedometer.isPedometerEventTrackingAvailable() && CMPedometer.isDistanceAvailable() && CMPedometer.isStepCountingAvailable()
    }

    private func initializePedometer() {
        if isPedometerAvailable {
            guard let startDate = Calendar.current.date(byAdding: .day, value: -1, to: Date()) else {
                return
            }
            
            pedometer.queryPedometerData(from: startDate, to: Date()) { (data, error) in
                guard let data = data, error == nil else { return }
                
                steps = data.numberOfSteps.intValue
            }
        }
    }
    
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
                    .font(.AppTitle)
                    .foregroundStyle(.black)
                
                // Card content
                let stepHistory = statViewModel.readStepHistory()
                NavigationLink(destination: StatsDetailScreen(stepHistory: stepHistory)) {
                    VStack(alignment: .leading, spacing: 20.0) {
                        
                        HStack {
                            Text("Steps")
                                .font(.AppBody)
                                .foregroundStyle(.black)
                            
                            Spacer()
                            
                            Text("Fitness Detail >")
                                .font(.AppBody)
                                .foregroundStyle(.black) // Direct to screen that shows fitness detail
                        }
                        
                        
                        HStack(alignment: .bottom) {
                            Text("\(steps)")
                                .font(.AppStatsSteps)
                                .foregroundStyle(.black)   // TODO: Read steps from sensor
                                .onAppear {
                                    initializePedometer()
                                }
                            Text("steps")
                                .font(.AppBody)
                                .foregroundStyle(.black)
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
                                    .font(.AppGraphGoal).foregroundColor(.secondary)
                            }
                        ForEach(stepHistory) { steps in
                            BarMark(
                                x: .value("Date", steps.date, unit: .day),
                                y: .value("Steps", steps.stepCount)
                            )
                            .foregroundStyle(Color.pink.gradient)
                            .cornerRadius(10)
                        }
                    }
                    .font(.AppGraphGoal)
                    .frame(height: 180)
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
