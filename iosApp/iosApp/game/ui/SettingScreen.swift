//
//  SettingScreen.swift
//  iosApp
//
//  Created by Hao Wu on 9/5/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct SettingScreen: View {
    
    @ObservedObject var settingState: SettingState
    
    let dailyGoalOption: [Int] = [
        5000,
        10000,
        15000,
        20000
    ]
    
    let weeklyGoalOption: [Int] = [
        20000,
        30000,
        40000,
        50000,
        60000,
        70000,
        80000,
        90000,
        100000
    ]
    
    init() {
        self.settingState = SettingState()
        
    }
    
    // TODO: UI needs fix
    var body: some View {
        
        NavigationView {
            
            VStack(alignment: .leading, spacing: 20.0) {
                
                // Display Step Counts
                HStack {
                    Toggle("Display Step Counts", isOn: $settingState.stepCountSwitchON)
                        .toggleStyle(SwitchToggleStyle(tint: .pink))
                }
                
                // Display Character
                HStack {
                    Toggle(isOn: $settingState.progressMonsterSwitchON) {
                        
                        if settingState.progressMonsterSwitchON {
                            Text("Display Character(Monster)")
                        } else {
                            Text("Display Character(Human)")
                        }
                       
                    }
                    .toggleStyle(SwitchToggleStyle(tint: .pink))
                }
                
                // Effective Days
                HStack{
                    Text("Effective Days")
                    Spacer()
                    TextField("Enter your name", value: $settingState.effectiveDays, formatter: NumberFormatter())
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                        .frame(width: 50)
//                        .disabled(DateTimeUtil.getDayDifference(next: DateTimeUtil.getCurrentDateTime(), previous: effectiveDate) >= effectiveDays)
                }
                
                // Change Goal
                VStack{
                    
                    HStack{
                        Text("Change Goal")
                        Spacer()
                        
                        
                    }.frame(height: 20)
                    
                    VStack {
                        VStack(alignment: .center) {
                            Tabs(
                                tabs: .constant(["Daily Goal", "Weekly Goal"]),
                                selection: $settingState.tabIndex,
                                underlineColor: .black) { title, isSelected in
                                    Text(title.uppercased())
                //                       .font(.system(size: 20))
                                       .fontWeight(.semibold)
                                       .foregroundColor(isSelected ? .black : .gray)
                                }

                            Text(settingState.tabIndex == 0 ? "\(Int(settingState.dailyGoal))" : "\(Int(settingState.weeklyGoal))").padding(.top, 20)
                            Slider(
                                value: settingState.tabIndex == 0 ? $settingState.dailyGoal : $settingState.weeklyGoal,
                                in: settingState.tabIndex == 0 ? 5000.0...30000 : 20000.0...100000.0,
                                step: settingState.tabIndex == 0 ? 5000.0 : 10000.0
                            )
                            .tint(Color.pink)
                            .padding(.horizontal, 20)
                            Spacer()
                        }
                        .padding(.top, 20)
                    }
                    
                    Spacer()
                }
                
                
                
                
                // Navigation button to the advanced settings
                NavigationLink(destination: SettingAdvancedScreen()) {
//                    Button("Advanced Settings") {}
                    Text("Advanced Settings")
                        .foregroundStyle(.white)
                        .padding(12)
                        .background(
                            RoundedRectangle(cornerRadius: 10)
                                .fill(Color.gray)
//                                .shadow(color: .gray, radius: 2, x: 0, y: 2)
                        )
                        .clipShape(Rectangle())
                }
                
                // Button to save all setting changes
                Button("Save!") {
                    settingState.writeDisplaySteps(displaySteps: settingState.stepCountSwitchON)
                    settingState.writeDisplayMonster(displayMonster: settingState.progressMonsterSwitchON)
                    settingState.writeEffectiveDate(effectiveDate: settingState.effectiveDate)
                    settingState.writeEffectiveDays(effectiveDays: settingState.effectiveDays)
                    settingState.writeDailyGoal(dailyGoal: Int(settingState.dailyGoal))
                    settingState.writeWeeklyGoal(weeklyGoal: Int(settingState.weeklyGoal))
                }
                .padding(12)
                .foregroundStyle(.white)
                .background(
                    RoundedRectangle(cornerRadius: 10)
                        .fill(Color.gray)
//                        .shadow(color: .gray, radius: 2, x: 0, y: 2)
                )
                .clipShape(Rectangle())
                
            }
                .padding()
                .navigationBarTitle("Settings")
                .frame(alignment: .topLeading)
            
        }
        
    }
    
}


struct SettingScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingScreen()
    }
}
