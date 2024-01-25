//
//  WelcomePage3.swift
//  iosApp
//
//  Created by Hao Wu on 9/19/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct WelcomePage3: View {
    var onPreviousButtonClicked: () -> Void = {}
    var onNextButtonClicked: () -> Void = {}
    
    @State private var tabIndex = 0
    @State private var dailyGoal = 5000.0
    @State private var weeklyGoal = 20000.0
    
    var body: some View {
        VStack {
            VStack {
                HStack {
                    Text("FITNESS PLAN")
                        .font(.WelcomeTitle)
                        .foregroundColor(.red)
                }
                
                Spacer().frame(height: 60)
                
                VStack {
                    Text("Tell ULA the number of steps you intend to take. No, No, No you can not enter anything less than 5,000 steps >:)")
                        .font(.WelcomeBody)
                }
                
                Spacer().frame(height: 60)
                
                VStack {
                    Tabs(
                        tabs: .constant(["Daily Goal", "Weekly Goal"]),
                        selection: $tabIndex,
                        underlineColor: .black) { title, isSelected in
                            Text(title.uppercased())
                                .font(.WelcomeTabTitle)
                               .fontWeight(.semibold)
                               .foregroundColor(isSelected ? .black : .gray)
                        }

                    Text(tabIndex == 0 ? "\(Int(dailyGoal))" : "\(Int(weeklyGoal))")
                        .font(.WelcomeGoal)
                        .padding(.top, 20)
                    Slider(
                        value: tabIndex == 0 ? $dailyGoal : $weeklyGoal,
                        in: tabIndex == 0 ? 5000...30000 : 20000...100000,
                        step: 4
                    )
                    .padding(.horizontal, 20)
                }
                
                Spacer()
                
                HStack {
                    BackButton (
                        onClick: {
                            onPreviousButtonClicked()
                        }
                    )
                    
                    Spacer()
                    
                    NextButton (
                        onClick: {
                            onNextButtonClicked()
                            UserDefaults.standard.set(dailyGoal, forKey: SavedDailyGoal)
                            UserDefaults.standard.set(weeklyGoal, forKey: SavedWeeklyGoal)
                        }
                    )
                }
            }
            .padding(.leading, 20)
            .padding(.trailing, 20)
            .padding(.top, 50)
            .padding(.bottom, 0)
        }
    }
}

struct WelcomePage3_Previews: PreviewProvider {
    static var previews: some View {
        WelcomePage3(
            onPreviousButtonClicked: {},
            onNextButtonClicked: {}
        )
    }
}
