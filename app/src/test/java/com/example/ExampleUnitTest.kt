package com.example

import com.example.data.repository.InitialData
import com.example.ml.MatchingEngine
import com.example.ml.ResumeParserEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun testMatchingEngine_weightedScoring() {
    val user = InitialData.sampleUser
    val job = InitialData.sampleJobs.first() // Senior ML Engineer at Anthropic

    val matchScore = MatchingEngine.calculateJobMatch(user, job)

    assertNotNull(matchScore)
    assertTrue("Match score should be between 0 and 100", matchScore.overallScore in 0..100)
    assertTrue("Should detect matched skills", matchScore.matchedSkills.isNotEmpty())
    assertTrue("Should compute skill score", matchScore.skillScore in 0..100)
    assertTrue("Should compute semantic score", matchScore.semanticScore in 0..100)
    assertTrue("Summary should not be empty", matchScore.matchSummary.isNotBlank())
  }

  @Test
  fun testResumeParserEngine_extraction() {
    val result = ResumeParserEngine.parseResumeText(ResumeParserEngine.SAMPLE_RESUME_TEXT, "Alex_Resume.pdf")

    assertNotNull(result)
    assertEquals("Alex Rivera", result.candidateName)
    assertTrue("Should extract skills", result.detectedSkills.size >= 5)
    assertTrue("Overall resume score should be high", result.overallScore in 70..100)
    assertTrue("Strengths should be detected", result.strengths.isNotEmpty())
    assertTrue("Improvement areas should be detected", result.improvementAreas.isNotEmpty())
  }
}
