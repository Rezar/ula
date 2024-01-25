//
//  StatDetailScreen.swift
//  iosApp
//
//  Created by Hao Wu on 9/7/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct StatsDetailScreen: View {
    
    var stepHistory: [StepHistory]
    
    var body: some View {
        
        let goal = 10000
        let columns = [GridItem(.flexible())]
        
        VStack(alignment: .leading, spacing: 20.0) {
            
            Text("Stats Detail")
                .font(.AppTitle)
                .foregroundStyle(.black)
            
            ScrollView {
                LazyVGrid(columns: columns) {
                    ForEach(stepHistory) { item in
                        
                        StatsDetailItem(
                            weekday: item.date.formatted(Date.FormatStyle().weekday(.wide)),
                            currentSteps: item.stepCount,
                            progressIndex: getMonsterProgress(goal: goal, currentSteps: item.stepCount),
                            goal: goal
                        )
                        
                    }
                }
                
            }
            
        }.padding()
        
    }
}

func getMonsterProgress(
    goal: Int,
    currentSteps: Int
) -> Int{
    
    let progressBarThreshold = [
        0.4,
        0.6,
        0.8,
        1.0,
        1.2
    ]
    
    if Double(currentSteps) < Double(goal) * progressBarThreshold[0] {
        return 0
    }

    if Double(currentSteps) < Double(goal) * progressBarThreshold[1]
        && Double(currentSteps) >= Double(goal) * progressBarThreshold[0] {
        return 1
    }

    if Double(currentSteps) < Double(goal) * progressBarThreshold[2]
        && Double(currentSteps) >= Double(goal) * progressBarThreshold[1] {
        return 2
    }

    if Double(currentSteps) < Double(goal) * progressBarThreshold[3]
        && Double(currentSteps) >= Double(goal) * progressBarThreshold[2] {
        return 3
    }

    if Double(currentSteps) < Double(goal) * progressBarThreshold[4]
        && Double(currentSteps) >= Double(goal) * progressBarThreshold[3] {
        return 4
    }

    if Double(currentSteps) >= Double(goal) * progressBarThreshold[4] {
        return 5
    }
    else {
        return 0
    }

    
}


struct StatsDetailItem: View {
    
    var weekday: String = ""
    var currentSteps: Int = 5000
    var progressIndex: Int = 0
    var goal: Int
    
    var body: some View {
        VStack(alignment: .leading, spacing: 5) {
            HStack(spacing: 25) {
                
                Text(weekday)
                    .font(.AppBody)
                    .frame(width: 100, alignment: .leading)
                // TODO: Add if statement to display or not display the number (详情请抄android代码)
                Text("\(currentSteps)")
                    .font(.AppBody)
                
            }
            
            MonsterProgressBar(selectedIndex: progressIndex) //TODO: ------------------------------(详情请抄android代码)
            
            progressBar(stepCount: currentSteps, goal: goal)
        }.frame(height: 150)
    }
    
}


struct MonsterProgressBar: View{
    
    var selectedIndex: Int
    
    //Stickman images
    let stickmanList = [
        "stickman_draft_bed",
        "stickman_draft_4",
        "stickman_draft_3",
        "stickman_draft_2",
        "stickman_draft_1",
        "stickman_draft_5"
    ]
    
    let monsterList = [
        "stickman_draft_monster_5",
        "stickman_draft_monster_4",
        "stickman_draft_monster_1",
        "stickman_draft_monster_3",
        "stickman_draft_monster_2",
        "stickman_draft_monster_6",
        
    ]
    
    var body: some View {
        // TODO: add if statement to allow displaying monster from settings userDefault (详情请抄android代码)
        displayImage(selectedIndex: selectedIndex, imageList: stickmanList)
    }
    
}

struct displayImage: View{
    var selectedIndex: Int
    var imageList: [String]
    
    var body: some View {
        HStack {
            ForEach(imageList.indices, id: \.self) { i in
                Image(imageList[i])
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .opacity(i <= selectedIndex ? 1 : 0.5)
            }
            
        }
    }
}


struct progressBar: View {
    let stepCount: Int
    let goal: Int

    var body: some View {
        VStack {
            if stepCount <= goal {
                ProgressView(value: Double(stepCount) / Double(goal), total: 1.0)
                    .tint(.pink)
            } else {
                ProgressView(value: 1.0, total: 1.0)
                    .tint(.pink)
            }
            
        }
    }
}

//struct StatDetailScreen_Previews: PreviewProvider {
//    static var previews: some View {
//
//        let stepHistory: [StepHistory] = [
//            .init(date: Date.from(year: 2023, month: 09, day: 07), stepCount: 3000),
//            .init(date: Date.from(year: 2023, month: 09, day: 08), stepCount: 4890),
//            .init(date: Date.from(year: 2023, month: 09, day: 09), stepCount: 9987),
//            .init(date: Date.from(year: 2023, month: 09, day: 10), stepCount: 15000),
//            .init(date: Date.from(year: 2023, month: 09, day: 11), stepCount: 24000),
//            .init(date: Date.from(year: 2023, month: 09, day: 12), stepCount: 5000)
//        ]
//        StatDetailScreen(stepHistory: stepHistory)
//    }
//}
